package etienne.clement.ecm_phone_shop.models;

/**
 * created by ETIENNE CLEMENT MBAYE on 22-03-2022.
 */
public class FormModel {
   private String valueTxt,valueInp;

    public String getValueTxt() {
        return valueTxt;
    }

    public String getValueInp() {
        return valueInp;
    }

    public FormModel(String valueTxt, String valueInp) {
        this.valueTxt = valueTxt;
        this.valueInp = valueInp;
    }
}
