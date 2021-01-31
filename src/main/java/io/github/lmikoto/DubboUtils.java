package io.github.lmikoto;


import java.util.List;
import java.util.Objects;

/**
 * @author liuyang
 * 2021/1/31 11:58 上午
 */
public class DubboUtils {
    private static final String INVOKE = "invoke ";

    private static final String DOT = ".";

    private static final String LEFT = "(";

    private static final String RIGHT = ")";

    private static final String BLANK = " ";

    private static final String CLASS = "class";

    private static final String QUOTATION = "\"";

    private static final String QUOTATION_CLASS = QUOTATION + CLASS + QUOTATION;

    private static final String COLON = ":";

    private static final String B_LEFT = "{";

    private static final String B_RIGHT = "}";

    private static final String COMMA = ",";

    public static String getEmptyInvoker(String className, String methodName) {
        return INVOKE + className + DOT + methodName + LEFT + RIGHT;
    }

    public static String getInvoker(ClassEntity classEntity,String methodQualified) {
        StringBuilder builder = new StringBuilder();
        builder.append(INVOKE)
                .append(methodQualified)
                .append(LEFT)
                .append(B_LEFT)
                .append(QUOTATION_CLASS)
                .append(COLON)
                .append(QUOTATION)
                .append(classEntity.getQualifiedName())
                .append(QUOTATION);
        List<FieldEntity> fields = classEntity.getFields();

        buildFiled(builder,fields);
        builder.append(B_RIGHT).append(RIGHT);
        return builder.toString();
    }

    private static void buildFiled(StringBuilder builder, List<FieldEntity> fields) {
        for (FieldEntity fieldEntity: fields){
            if(Objects.nonNull(fieldEntity.getValue())){
                builder.append(COMMA)
                        .append(QUOTATION)
                        .append(fieldEntity.getFieldName())
                        .append(QUOTATION)
                        .append(COLON);

                if(fieldEntity.getType().equals(String.class.getName())){
                    builder.append(QUOTATION)
                            .append(fieldEntity.getValue())
                            .append(QUOTATION);
                }else {
                    builder.append(fieldEntity.getValue());
                }
            }
        }
    }


}
