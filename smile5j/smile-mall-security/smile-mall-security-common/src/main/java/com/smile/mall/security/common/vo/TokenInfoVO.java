
package com.smile.mall.security.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TokenInfoVO {

    @Schema(description = "accessToken" )
    private String accessToken;

    @Schema(description = "refreshToken" )
    private String refreshToken;

    @Schema(description = "在多少秒后过期" )
    private Integer expiresIn;
}
