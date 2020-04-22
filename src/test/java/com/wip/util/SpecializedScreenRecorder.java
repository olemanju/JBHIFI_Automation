package com.wip.util;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.screenrecorder.ScreenRecorder;

public class SpecializedScreenRecorder extends ScreenRecorder {
	public String name;

	public SpecializedScreenRecorder(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat,
			Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder, String name)
			throws IOException, AWTException {
		super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
		this.name = name;

	}

	@Override
	public File createMovieFile(Format fileFormat) throws IOException {
		/*
		 * if (movieFolder.exists()) { try { String SRC_FOLDER =
		 * System.getProperty("user.dir")+"\\src\\test\\resources\\Recordings"; File
		 * directory = new File(SRC_FOLDER); delete(directory); } catch (Exception e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * File file = new
		 * File(System.getProperty("user.dir")+"\\src\\test\\resources\\Recordings");
		 * file.delete();
		 * 
		 * }
		 */
		if (!movieFolder.exists()) {
			movieFolder.mkdirs();
		} else if (!movieFolder.isDirectory()) {
			throw new IOException("\"" + movieFolder + "\" is not a directory.");
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
		/*
		 * return new File(movieFolder, name + "-"+
		 * Registry.getInstance().getExtension(fileFormat));
		 */

		return new File(movieFolder, name + "-" + dateFormat.format(new Date()) + "_" + "."
				+ Registry.getInstance().getExtension(fileFormat));
	}

	public void delete(File file) {

		String SRC_FOLDER = System.getProperty("user.dir") + "\\src\\test\\resources\\Recordings";
		File directory = new File(SRC_FOLDER);
		if (!directory.exists()) {

			System.out.println("Directory does not exist.");
			System.exit(0);

		} else {

			try {
				if (file.isDirectory()) {

					// directory is empty, then delete it
					if (file.list().length == 0) {

						file.delete();
						System.out.println("Directory is deleted : " + file.getAbsolutePath());

					} else {

						// list all the directory contents
						String files[] = file.list();

						for (String temp : files) {
							// construct the file structure
							File fileDelete = new File(file, temp);

							// recursive delete
							delete(fileDelete);
						}

						// check the directory again, if empty then delete it
						if (file.list().length == 0) {
							file.delete();
							System.out.println("Directory is deleted : " + file.getAbsolutePath());
						}
					}

				} else {
					// if file, then delete it
					file.delete();
					System.out.println("File is deleted : " + file.getAbsolutePath());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
