package njkgkj.com.aircargoplusapp.tools.recognize.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import njkgkj.com.aircargoplusapp.Utils.AviationCommons;

import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.DST_FOLDER_NAME;
import static njkgkj.com.aircargoplusapp.Utils.AviationCommons.StoragePath;

public class FileUtil {
	private static final  String TAG = "Recognize--";
//	private static final File parentPath = Environment.getExternalStorageDirectory();
//	private static   String storagePath = "";

	/**初始化保存路径
	 * @return
	 */
//	private static String initPath(){
//		if(storagePath.equals("")){
//			storagePath = parentPath.getAbsolutePath()+"/" + DST_FOLDER_NAME;
//			File f = new File(storagePath);
//			if(!f.exists()){
//				f.mkdir();
//			}
//		}
//		return storagePath;
//	}

	/**保存Bitmap到sdcard
	 * @param b
	 */
	public static void saveBitmap(Bitmap b,String sFlag){

		File f = new File(StoragePath);
		if(!f.exists()){
			f.mkdir();
		}
		long dataTake = System.currentTimeMillis();
		String jpegName = "";
		if (sFlag.equals("number")) {
			jpegName = StoragePath + "/" + "number" +".jpg";
		} else if (sFlag .equals("card")) {
			jpegName = StoragePath + "/" + "card" +".jpg";
		}

		Log.i(TAG, "saveBitmap:jpegName = " + jpegName);
		try {
			FileOutputStream fout = new FileOutputStream(jpegName);
			BufferedOutputStream bos = new BufferedOutputStream(fout);
			if (sFlag.equals("number")) {
				b.compress(Bitmap.CompressFormat.JPEG, 50, bos);
			} else if (sFlag .equals("card")) {
				b.compress(Bitmap.CompressFormat.JPEG, AviationCommons.Camera_Quality, bos);
			}

			bos.flush();
			bos.close();
			Log.i(TAG, "saveBitmap成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i(TAG, "saveBitmap:失败");
			e.printStackTrace();
		}

	}
}
