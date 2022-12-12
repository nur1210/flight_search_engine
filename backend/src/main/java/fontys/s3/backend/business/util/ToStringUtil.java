package fontys.s3.backend.business.util;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.Field;

public class ToStringUtil {
    public static String toStringWithAttributes(Object ofInterest, ToStringStyle style) {
        ReflectionToStringBuilder builder = new ReflectionToStringBuilder(ofInterest, style) {

            @Override
            protected boolean accept(Field field) {
                try {
                    return super.accept(field) && field.get(ofInterest) != null;
                } catch (IllegalAccessException e) {
                    return super.accept(field);
                }
            }


        };
        ToStringStyle a = new ToStringStyle() {
            @Override
            protected void setFieldSeparator(String fieldSeparator) {
                try{
                    super.setFieldSeparator("&");
                } catch (Exception e) {
                    super.setFieldSeparator(fieldSeparator);
                }
            }
        };

        return builder.toString();
    }
}
