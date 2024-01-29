import { verify } from '@services/jwt.service';
import { Payload } from '@models/payload';
import catchAsync from '@utils/catchAsync';
import serverConfig from '@configs/server.config';

export default () => {

    return catchAsync(async (req, res, next) => {
        
        let clientKey = req.header('client-key')

        if (clientKey && clientKey == serverConfig.clientKey) {
            return next()
        }

        let token = req.header('authorization')

        if (!token) {
            return res.status(401).send({
                message: 'Token not provided'
            })
        }

        try {
            let payload: Payload = await verify(token)
            res.locals.payload = payload
            
            next()
        }
        catch(err) {
            return res.status(401).send({ 
                message: 'Invalid token'
            })
        }
    })
}

