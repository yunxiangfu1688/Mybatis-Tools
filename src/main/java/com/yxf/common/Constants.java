package com.yxf.common;

public interface Constants {

    String   MATERIAL_QUEUE                 = "MATERIAL_QUEUE";
    String   CZPS_QUEUE                     = "CZPS.QUEUE";
    String   LJZD_QUEUE                     = "LJZD.QUEUE";
    String   YXGZTJ_QUEUE                   = "YXGZTJ.QUEUE";
    String   CM_BOOM_QUEUE                  = "CM_BOOM.QUEUE";
    String   RE_BOOM_QUEUE                  = "RE_BOOM.QUEUE";

    String   TABLE_CZPS                     = "CZPS_TEST";

    String   TABLE_CPZD                     = "CPZD_TEST";

    String   TABLE_PJZD                     = "PJZD_TEST";

    String   TABLE_TZBM                     = "TZBM_TEST";

    String[] CZPS_COLUMNS                   = { "ID", "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI", "AJ", "ZV",
                                                "ZW", "ZX", "ZY", "ZZ", "BA", "BF", "AK", "AL", "SA", "SB", "CA", "CB",
                                                "FT", "BB", "BC" };
    String   CPZD_TYPE                      = "CRH380BG:CRH5G:CRH5A:CR400BF:CRH3A:CRH5G提升";
    /**
     * PHM-预测故障
     */
    String   PHM_PROGNOSIS_TOPIC            = "PHM_PROGNOSIS_TOPIC";
    /**
     * PHM-维修建议
     */
    String   PHM_REPAIR_ADVICE_TOPIC        = "PHM_REPAIR_ADVICE_TOPIC";
    /**
     * PHM-TTF（寿命件次数）
     */
    String   PHM_TTF_TOPIC                  = "PHM_TTF_TOPIC";
    /**
     * PHM-车轮磨耗规律数据
     */
    String   PHM_WHEEL_ABRASION_LAW_TOPIC   = "PHM_WHEEL_ABRASION_LAW_TOPIC";
    /**
     * PHM-轮对尺寸到限数据
     */
    String   PHM_WHEEL_SIZE_LIMIT_HIS_TOPIC = "PHM_WHEEL_SIZE_LIMIT_HIS_TOPIC";

}
