package jp.co.accolade.dev.sample01.cnst;

/**
 * 星座.
 */
public enum Constellation {

    ARIES("牡羊座"),
    TAURUS("牡牛座"),
    GEMINI("双子座"),
    CANCER("蟹座"),
    LEO("獅子座"),
    VIRGO("乙女座"),
    LIBRA("天秤座"),
    SCORPIUS("蠍座"),
    SAGITTARIUS("射手座"),
    CAPRICORNUS("山羊座"),
    AQUARIUS("水瓶座"),
    PISCES("魚座"),

    ;

    public String name;

    Constellation(String name) {
        this.name = name;
    }


}
