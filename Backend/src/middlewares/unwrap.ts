type UnwrapPromise<P extends any> = P extends Promise<infer R> ? R : P;

type UnwrapTuple<Tuple extends readonly unknown[]> = {
  [K in keyof Tuple]: Tuple[K] extends Promise<infer X> ? X : UnwrapPromise<Tuple[K]>;
};

let tuple: UnwrapTuple<number[]> = [
  
]

function transaction2<P extends any[]>(...arg: P): UnwrapTuple<P> {
  const unwrapped: UnwrapTuple<P> = arg as any;
  return unwrapped;
}

async function transaction<P extends Promise<any>[]>(arg: [...P]): Promise<UnwrapTuple<P>> {
    const unwrapped: UnwrapTuple<P> = await Promise.all(arg) as any
    return unwrapped;
}

async function test() {
    const res = await transaction([
        Promise.resolve(100),
        Promise.resolve(200),
        Promise.resolve(333),
        Promise.resolve(421),
    ])

    console.log(res[0])
    console.log(res[1])
    console.log(res[2])
    console.log(res[3])
}

test()