package com.xgs.hisystem.util.card;

import com.sun.jna.Native;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xgs
 * @date 2019-5-18
 * @description:
 */
public class Card {

    private static final Logger logger = LoggerFactory.getLogger(Card.class);

    /**
     * 默认获取IC卡号
     *
     * @return
     */
    public static String defaultGetCardId() {
        Dcrf32_h dcrf32_h;
        try {
            dcrf32_h = (Dcrf32_h) Native.loadLibrary("dcrf32", Dcrf32_h.class);
        } catch (Exception e) {
            return "fail";
        }

        int result;
        int handle;
        int[] snr = new int[1];
        byte[] send_buffer = new byte[2048];
        byte[] recv_buffer = new byte[2048];


        result = dcrf32_h.dc_init((short) 100, 115200);
        if (result < 0) {
            logger.info("dc_init ...error ");
            return "fail";
        }

        handle = result;

        result = dcrf32_h.dc_config_card(handle, (byte) 0x41);//设置非接卡型为A
        result = dcrf32_h.dc_card(handle, (byte) 0, snr);
        if (result != 0) {
            logger.info("dc_card ...error ");
            dcrf32_h.dc_exit(handle);
            return "none";
        }


        recv_buffer[0] = (byte) ((snr[0] >>> 24) & 0xff);
        recv_buffer[1] = (byte) ((snr[0] >>> 16) & 0xff);
        recv_buffer[2] = (byte) ((snr[0] >>> 8) & 0xff);
        recv_buffer[3] = (byte) ((snr[0] >>> 0) & 0xff);
        String cardid = print_bytes(recv_buffer, 4);

        if (cardid.equals("00000000")) {
            logger.info("未识别到卡片！");
            return "none";
        }

        /* 蜂鸣*/
        dcrf32_h.dc_beep(handle, (short) 10);
        if (result != 0) {
            logger.info("dc_beep ...error ");
            dcrf32_h.dc_exit(handle);
            return "fail";
        }

        result = dcrf32_h.dc_exit(handle);
        if (result != 0) {
            logger.info("dc_exit ...error ");
            return "fail";
        }

        return cardid;
    }

    private static String print_bytes(byte[] b, int length) {
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < length; ++i) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            temp.add(hex.toUpperCase());
        }
        return String.join("", temp);
    }
}
