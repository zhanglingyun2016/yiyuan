package com.xgs.hisystem.pojo.bo;


public class BaseResponse<T> {
    
    private Integer status;//0--失败， 1--成功  
    private String message;//消息
    private T data;//返回的具体数据

    /**
     * 系统返回成功失败
     */
    public static final Integer RESPONSE_SUCCESS=1;//成功
    public static final Integer RESPONSE_FAIL=0;//失败

    public static <T> BaseResponse<T> error(String msg){
        BaseResponse<T> res = new BaseResponse<T>();
        res.setStatus(RESPONSE_FAIL);
        res.setMessage(msg);
        return res;
    }

    /**
     * @param
     * @return
     */
    public  static <T> BaseResponse<T> success(){
        BaseResponse<T> res = new BaseResponse<T>();
        res.setStatus(RESPONSE_SUCCESS);
        res.setMessage("操作成功");
        return res;
    }
    /**
     * @param data
     * @return
     */
    public  static <T> BaseResponse<T> success(T data){
        BaseResponse<T> res = new BaseResponse<T>();
        res.setStatus(RESPONSE_SUCCESS);
        res.setMessage("操作成功");
        res.setData(data);
        return res;
    }
    
    
    
    public static <T> BaseResponse<T> success(T data, String msg){
        BaseResponse<T> res = new BaseResponse<T>();
        res.setStatus(RESPONSE_SUCCESS);
        res.setMessage(msg);
        res.setData(data);
        return res;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status='" + status + '\'' +
                ", msg='" + message + '\'' +
                ", data=" + data +
                '}';
    }
    
}
