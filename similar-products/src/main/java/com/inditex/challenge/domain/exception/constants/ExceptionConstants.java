package com.inditex.challenge.domain.exception.constants;

public final class ExceptionConstants {
    private ExceptionConstants() {}

    // aggregate
    public static final String PRODUCT_ID_NULL_DESC = "domain.exception.productId.notNull.message";
    public static final String PRODUCT_NAME_NULL_DESC = "domain.exception.productName.notNull.message";
    public static final String PRODUCT_PRICE_NULL_DESC = "domain.exception.productPrice.notNull.message";

    // vo
    public static final String VALUE_PRODUCT_ID_NULL_DESC = "domain.exception.productId.value.notNull.message";
    public static final String VALUE_PRODUCT_NAME_NULL_DESC = "domain.exception.productName.value.notNull.message";
    public static final String VALUE_PRODUCT_PRICE_NEGATIVE_DESC = "domain.exception.productPrice.value.negative.message";
    public static final String SIMILAR_PRODUCTS_NULL_DESC = "domain.exception.similar.productId.notNull.message";
}
