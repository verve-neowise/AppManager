import * as react from 'react';

const useisoverflow = (ref: any, isVerticalOverflow: boolean = true, callback: any = undefined) => {
  const [isoverflow, setisoverflow] = react.useState(false);

  react.useLayoutEffect(() => {
    const { current } = ref;
    const { clientWidth, scrollWidth, clientHeight, scrollHeight } = current;

    console.log(current)
    console.log(clientHeight, scrollHeight)

    const trigger = () => {
      const hasoverflow = isVerticalOverflow ? scrollHeight > clientHeight : scrollWidth > clientWidth;

      setisoverflow(hasoverflow);

      if (callback) callback(hasoverflow);
    };

    if (current) {
      trigger();
    }
  }, [callback, ref, isVerticalOverflow]);

  return isoverflow;
};

export default useisoverflow;