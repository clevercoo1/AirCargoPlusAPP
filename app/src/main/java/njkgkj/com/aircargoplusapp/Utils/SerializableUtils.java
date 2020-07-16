package njkgkj.com.aircargoplusapp.Utils;

/**
 * Created by 46296 on 2020/7/15.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化类的复制
 *
 * @author D10NG
 * @date on 2019-07-22 15:19
 */
public class SerializableUtils {

    /**
     * 复制
     * @param old
     * @return
     */
    public static Object copy(Object old) {
        Object clazz = null;
        try {
            // 写入字节流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(old);
            // 读取字节流
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            clazz = (Object) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

}

