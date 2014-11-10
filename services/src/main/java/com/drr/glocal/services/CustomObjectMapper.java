/**
 *
 */
package com.drr.glocal.services;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        super();
//        getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        setVisibilityChecker(
                getSerializationConfig().getDefaultVisibilityChecker()
                        .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
                        .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                        .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                        .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                        .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
    }

}
