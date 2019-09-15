package com.yxf.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class SerializeUtil {

    public static <T> byte[] serialize(List<T> value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            for (T obj : value) {
                os.writeObject(obj);
            }
            os.writeObject(null);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        }
        return rv;
    }

    /**
     * 反序列化列表（用于Redis整存整取）
     * 
     * @param in
     * @return
     */
    public static <T> List<T> unserializeForList(byte[] in) {
        List<T> list = new ArrayList<T>();
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                while (true) {
                    T obj = (T) is.readObject();
                    if (obj == null) {
                        break;
                    } else {
                        list.add(obj);
                    }
                }
                is.close();
                bis.close();
            }
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return list;
    }

    /**
     * 序列化
     * 
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 反序列化
     * 
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /***
     * 压缩GZip
     *
     * @param data
     * @return
     */
    public static byte[] gZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            gzip.close();
            b = bos.toByteArray();
            bos.close();
            // float f = b.length / (float) data.length;
            // RedisUtil.lpush("FACTOR", "" + f, null, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * 解压GZip,字节
     *
     * @param data
     * @return
     */
    public static byte[] unGZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            gzip.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * 解压GZip，InputStream
     *
     * @param data
     * @return
     */
    public static byte[] unGZip(InputStream dis) {
        byte[] b = null;
        StringBuffer content = new StringBuffer();
        try {
            // 建立gzip解压工作流
            GZIPInputStream gzip = new GZIPInputStream(dis);
            byte[] buf = new byte[512];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // InputStreamReader isr = new InputStreamReader(gzip, "UTF-8");
            // BufferedReader br = new BufferedReader(isr);
            // String str = "";
            // while ((str = br.readLine()) !=null) {
            // Log.info(str);
            // content.append(str+"\n");
            // }
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                // Log.info("num:"+num);
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            gzip.close();
            dis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }
}
