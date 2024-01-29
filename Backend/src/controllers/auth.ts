import { compareSync } from "bcrypt";
import { Payload } from '@models/index';
import { sign } from '@services/jwt.service';
import catchAsync from '@utils/catchAsync';
import { createUser, findUser } from '@services/user.service';


export const login = catchAsync(async (req, res, next) => {
    
    const { email, password } = req.body

    const user = await findUser(email)

    if (!user) {
        return res.json({
            success: false,
            message: "Account not found"
        })
    }

    const matchPasswords = compareSync(password, user.password)

    if (!matchPasswords) {
        return res.json({
            success: false,
            message: "Wrong authentication credentials"
        })
    }

    const payload: Payload = {
        userId: user.id,
        name: user.name,
        email: user.email
    }

    return res.json({
        success: true,
        token: await sign(payload),
    })
})


export const register = catchAsync(async (req, res, next) => {
    
    const { name, email, password } = req.body
    
    const user = await findUser(email)

    if (user) {
        return res.json({
            success: false,
            message: "Phone number already exists"
        })
    }

    const newUser = await createUser(name, email, password)

    const payload: Payload = {
        userId: newUser.id,
        name: newUser.name,
        email: newUser.email
    }

    return res.json({
        success: true,
        token: await sign(payload),
    })
})