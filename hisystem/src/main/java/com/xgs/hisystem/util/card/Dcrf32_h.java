/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgs.hisystem.util.card;

import com.sun.jna.win32.StdCallLibrary;

/**
 * @author Administrator
 */
public interface Dcrf32_h extends StdCallLibrary {

    int dc_init(short port, int baud);

    short dc_beep(int icdev, short ms);

    short dc_reset(int icdev, short ms);

    short dc_config_card(int icdev, byte cardtype);

    short dc_card(int icdev, byte mode, int[] snr);

    short dc_pro_reset(int icdev, byte[] rlen, byte[] receive_data);

    short dc_pro_command(int icdev, byte slen, byte[] sendbuffer, byte[] rlen, byte[] receive_data, byte timeout);

    short dc_pro_commandsource(int icdev, byte slen, byte[] sendbuffer, byte[] rlen, byte[] receive_data, byte timeout);

    short dc_pro_halt(int icdev);

    short dc_exit(int icdev);

    short dc_card_double(int icdev, byte mode, byte[] snr);

    short dc_load_key(int icdev, byte mode, byte secnum, byte[] pass);

    short dc_authentication(int icdev, byte mode, byte secnum);

    short dc_read(int icdev, byte addr, byte[] data);

    short dc_write(int icdev, byte addr, byte[] data);

    short dc_request_b(int icdev, byte _Mode, byte AFI,
                       byte N, byte[] ATQB);

    short dc_attrib(int icdev, byte[] PUPI, byte CID);

    short dc_pro_commandlink(int idComDev, byte slen,
                             byte[] sendbuffer, byte[] rlen,
                             byte[] databuffer, byte timeout,
                             byte FG);

    short dc_setcpu(int icdev, byte _Byte);

    short dc_cpureset(int icdev, byte[] rlen, byte[] databuffer);

    short dc_cpuapdu(int icdev, byte slen, byte[] sendbuffer,
                     byte[] rlen, byte[] databuffer);

    short dc_read_4442(int icdev, short offset, short lenth, byte[] buffer);

    short dc_read_4442_hex(int icdev, short offset, short lenth, byte[] buffer);

    short dc_write_4442(int icdev, short offset, short lenth, byte[] buffer);

    short dc_write_4442_hex(int icdev, short offset, short lenth, byte[] buffer);

    short dc_verifypin_4442(int icdev, byte[] passwd);

    short dc_verifypin_4442_hex(int icdev, byte[] passwd);

    short dc_readpin_4442(int icdev, byte[] passwd);

    short dc_readpin_4442_hex(int icdev, byte[] passwd);

    short dc_readpincount_4442(int icdev);

    short dc_changepin_4442(int icdev, byte[] passwd);

    short dc_changepin_4442_hex(int icdev, byte[] passwd);

    short dc_readwrotect_4442(int icdev, short offset, short leng, byte[] buffer);

    short dc_readwrotect_4442_hex(int icdev, short offset, short leng, byte[] buffer);

    short dc_writeprotect_4442(int icdev, short offset, short leng, byte[] buffer);

    short dc_writeprotect_4442_hex(int icdev, short offset, short leng, byte[] buffer);

    short dc_read_4428(int icdev, short offset, short lenth, byte[] buffer);

    short dc_read_4428_hex(int icdev, short offset, short lenth, byte[] buffer);

    short dc_write_4428(int icdev, short offset, short lenth, byte[] buffer);

    short dc_write_4428_hex(int icdev, short offset, short lenth, byte[] buffer);

    short dc_verifypin_4428(int icdev, byte[] passwd);

    short dc_verifypin_4428_hex(int icdev, byte[] passwd);

    short dc_readpin_4428(int icdev, byte[] passwd);

    short dc_readpin_4428_hex(int icdev, byte[] passwd);

    short dc_readpincount_4428(int icdev);

    short dc_changepin_4428(int icdev, byte[] passwd);

    short dc_changepin_4428_hex(int icdev, byte[] passwd);

    short dc_Check_4442(int icdev);

    short dc_Check_4428(int icdev);

    short dc_card_b_hex(int icdev, byte[] rbuf);

    short dc_card_b(int icdev, byte[] rbuf);

    short dc_CheckCard(int icdev);

    short dc_read_24c(int icdev, short offset, short lenth, byte[] buffer);

    short dc_read_24c64(int icdev, short offset, short lenth, byte[] buffer);

    short dc_write_24c(int icdev, short offset, short lenth, byte[] buffer);

    short dc_write_24c64(int icdev, short offset, short lenth, byte[] buffer);

}
