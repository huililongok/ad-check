package ad.home.common.fixed;

public enum ErrorStatus {
    SUCCESS("OK", 1),
    FAIL("失败", 2),
    FEIGNSERVER_ERR("服务端实例异常", 3),
    REDIRECT("重定向", 100),
    EXCEPTION("系统错误", 0),
    SESSION_INVALID("会话失效", -99),
    UN_LOGIN("您尚未登录", 401),
    PERMISSIONS("权限错误", 506);

    private String name;
    private Integer value;

    ErrorStatus(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }
    public Integer getValue() {
        return this.value;
    }

}
