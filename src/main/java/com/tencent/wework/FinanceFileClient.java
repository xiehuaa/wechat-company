package com.tencent.wework;

import com.xh.wechat.company.constant.ChatMessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-14 17:24
 */
@Slf4j
public class FinanceFileClient {
    private static final String FILE_BASE_PATH;

    static {
        String osName = System.getProperties().getProperty("os.name");
        if (osName.toUpperCase().contains("WIN")) {
            FILE_BASE_PATH = "C:\\file";
        } else {
            FILE_BASE_PATH = "/tmp/file";
        }
        try {
            File file = new File(FILE_BASE_PATH);
            if (!file.exists()) {
                FileUtils.forceMkdir(file);
            }
        } catch (Exception e) {
            log.error("创建文件目录失败：", e);
        }
    }

    private long sdk;

    private FinanceFileClient() {

    }

    /**
     * 获取FinanceFileClient对象
     *
     * @param sdk sdk
     * @return FinanceFileClient对象
     */
    public static FinanceFileClient newInstance(long sdk) {
        FinanceFileClient financeFileClient = new FinanceFileClient();
        financeFileClient.sdk = sdk;

        return financeFileClient;
    }

    /**
     * 获取FinanceFileClient对象
     *
     * @param corpId 企业id
     * @param secret 企业secret
     * @return FinanceFileClient对象
     */
    public static FinanceFileClient newInstance(String corpId, String secret) {
        long sdk = Finance.NewSdk();
        int state = Finance.Init(sdk, corpId, secret);

        FinanceFileClient financeFileClient = new FinanceFileClient();
        financeFileClient.sdk = sdk;

        return financeFileClient;
    }

    /**
     * 转存文件
     *
     * @param msgType   消息类型
     * @param sdkFileId 文件id
     * @param subType   子类型
     * @param fileExt   扩展属性
     * @param seq       seq
     * @return 文件路径
     */
    public String transferFile(String msgType, String sdkFileId, Integer subType, String fileExt, long seq) {
        if (StringUtils.isBlank(sdkFileId) || StringUtils.isBlank(msgType)) {
            return "";
        }
        if (StringUtils.isBlank(fileExt)) {
            fileExt = this.getFileExtend(msgType, subType);
        }

        String fileName = seq + "." + fileExt;
        String indexBuf = "";
        while (true) {
            long mediaData = Finance.NewMediaData();
            int ret = Finance.GetMediaData(sdk, indexBuf, sdkFileId, "", "", 60, mediaData);
            if (ret != 0) {
                log.info("获取文件失败，ret:【{}】", ret);
                return "";
            }
            try {

                this.saveToLocal(FILE_BASE_PATH + "/" + fileName, Finance.GetData(mediaData));

                if (Finance.IsMediaDataFinish(mediaData) == 1) {
                    Finance.FreeMediaData(mediaData);
                    // TODO: 上传至文件服务，并删除本地文件

                    return FILE_BASE_PATH + "/" + fileName;
                } else {
                    indexBuf = Finance.GetOutIndexBuf(mediaData);
                    Finance.FreeMediaData(mediaData);
                }
            } catch (Exception e) {
                log.error("保存文件失败：", e);
                return "";
            }
        }
    }

    /**
     * 获取文件的扩展名
     *
     * @param msgType 消息类型
     * @param subType 类型
     * @return 扩展名
     */
    private String getFileExtend(String msgType, Integer subType) {
        String extend = "";
        if (Objects.equals(msgType, ChatMessageConstant.MSG_TYPE_IMAGE)) {
            extend = "png";
        } else if (Objects.equals(msgType, ChatMessageConstant.MSG_TYPE_MEETING_VOICE_CALL)
                || (Objects.equals(msgType, ChatMessageConstant.MSG_TYPE_VIDEO))) {
            extend = "mp4";
        } else if (Objects.equals(msgType, ChatMessageConstant.MSG_TYPE_VOICE)) {
            extend = "amr";
        } else if (Objects.equals(msgType, ChatMessageConstant.MSG_TYPE_EMOTION)) {

            if (Objects.equals(subType, 1)) {
                extend = "gif";
            } else if (Objects.equals(subType, 2)) {
                extend = "png";
            }
        }
        return extend;
    }

    /**
     * 保存到本地
     *
     * @param filePath 文件路径
     * @param bytes    byte数组
     * @throws IOException
     */
    private void saveToLocal(String filePath, byte[] bytes) throws IOException {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File(filePath), true);
            outputStream.write(bytes);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
