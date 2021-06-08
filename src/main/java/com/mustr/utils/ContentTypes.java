package com.mustr.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

import cn.hutool.core.io.IoUtil;

/**
 *  获取contentType
 * @author mustr
 * @Date 2021-6-7
 *
 */
public class ContentTypes {

    /* The default MIME type */
    public static final String DEFAULT_MIMETYPE = "application/octet-stream";

    private static ContentTypes mimetypes = null;

    private HashMap<String, String> extensionToMimetypeMap = new HashMap<String, String>();

    private ContentTypes() {
    }

    public synchronized static ContentTypes getInstance() {
        if (mimetypes != null) {
            return mimetypes;
        }

        mimetypes = new ContentTypes();
        InputStream is = mimetypes.getClass().getResourceAsStream("/plugins/media.types");
        if (is != null) {
            try {
                mimetypes.loadMimetypes(is);
            } finally {
                IoUtil.close(is);
            }
        }
        return mimetypes;
    }

    public void loadMimetypes(InputStream is) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is));) {
            String line = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("#") || line.length() == 0) {
                    // Ignore comments and empty lines.
                } else {
                    StringTokenizer st = new StringTokenizer(line, " \t");
                    if (st.countTokens() > 1) {
                        String extension = st.nextToken();
                        if (st.hasMoreTokens()) {
                            String mimetype = st.nextToken();
                            extensionToMimetypeMap.put(extension.toLowerCase(), mimetype);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getMimetype(String filename) {
        String mimeType = getMimetypeByExt(filename);
        if (mimeType != null) {
            return mimeType;
        }
        return DEFAULT_MIMETYPE;
    }

    public String getMimetype(File file) {
        return getMimetype(file.getName());
    }

    public String getMimetype(File file, String key) {
        return getMimetype(file.getName(), key);
    }

    public String getMimetypeByExtName(String extName) {
        String lowerCase = extName.toLowerCase();
        if (extensionToMimetypeMap.keySet().contains(lowerCase)) {
            String mimetype = (String) extensionToMimetypeMap.get(lowerCase);
            return mimetype;
        }
        return DEFAULT_MIMETYPE;
    }
    
    public String getMimetype(String primaryObject, String secondaryObject) {
        String mimeType = getMimetypeByExt(primaryObject);
        if (mimeType != null) {
            return mimeType;
        }

        mimeType = getMimetypeByExt(secondaryObject);
        if (mimeType != null) {
            return mimeType;
        }

        return DEFAULT_MIMETYPE;
    }

    private String getMimetypeByExt(String fileName) {
        int lastPeriodIndex = fileName.lastIndexOf(".");
        if (lastPeriodIndex > 0 && lastPeriodIndex + 1 < fileName.length()) {
            String ext = fileName.substring(lastPeriodIndex + 1).toLowerCase();
            if (extensionToMimetypeMap.keySet().contains(ext)) {
                String mimetype = (String) extensionToMimetypeMap.get(ext);
                return mimetype;
            }
        }
        return null;
    }
}
