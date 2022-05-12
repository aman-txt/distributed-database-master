package utils;

import query.container.WhereCond;

public class UtilsCondition {

    public static Boolean evaluateCondition(WhereCond operator , String lhs, String rhs)
    {
        Boolean isNumber = true;

        double lhsNo = 0;
        double rhsNo = -1;

        try{
            lhsNo = Double.parseDouble(lhs);
            rhsNo = Double.parseDouble(rhs);
        }
        catch(NumberFormatException e)
        {
            isNumber = false;
        }

        switch(operator)
        {
            case EQUALS:
                if(isNumber == false)
                    return lhs.equals(rhs);
                return lhsNo == rhsNo;
            case GREATER_THAN:
                return lhsNo > rhsNo;
            case LESS_THAN:
                return lhsNo < rhsNo;
            case GREATER_THAN_EQUALS:
                return lhsNo >= rhsNo;
            case LESS_THAN_EQUALS:
                return lhsNo <= rhsNo;
            default:
                return false;
        }
    }

}
