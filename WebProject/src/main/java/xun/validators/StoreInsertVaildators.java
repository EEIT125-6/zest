package xun.validators;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import xun.model.StoreBean;

@Component
public class StoreInsertVaildators  implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return StoreBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		StoreBean sb = (StoreBean)target;
//		List<String> Telnum = new ArrayList<String>();
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stname", "", "商家名稱不可空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "saddress", "", "地址不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tel", "", "電話不能空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stitd","", "寫點介紹吧");
		if(sb.getStname()!=null) {
			if(sb.getStname().contains("<!--")) {
				errors.rejectValue("stname", "", "包含不合理字串請修正");
			}
		}
		if(sb.getSaddress()!=null) {
			if(sb.getSaddress().contains("<!--")) {
				errors.rejectValue("saddress", "", "包含不合理字串請修正");
			}
		}
		if(sb.getTel()!=null) {
			if(sb.getTel().contains("<!--")) {
				errors.rejectValue("tel", "", "包含不合理字串請修正");
			}
		}
		if(sb.getStitd()!=null) {
			if(sb.getStitd().contains("<!--")) {
				errors.rejectValue("stitd", "", "包含不合理字串請修正");
			}
		}
		if(sb.getStitddt()!=null) {
			if(sb.getStitddt().contains("<!--")) {
				errors.rejectValue("stitddt", "", "包含不合理字串請修正");
			}
		}
		
		if (sb.getTel().length()>11) {
			errors.rejectValue("tel", "","請確定輸入是否正確");
		}
		
//		for(int i = 0 ; i == sb.getTel().length() ; i++) {
//			Telnum.add(sb.getTel().substring(i, i+1));
//		}
		try {
			Integer.valueOf(sb.getTel().replaceFirst("-", ""));
		} catch (Exception e) {
			errors.rejectValue("tel","","電話只能包含數字");
		}
		if(sb.getStitd().length()>49) {
			errors.rejectValue("stitd","", "簡介字數超過50請修改");
//			ValidationUtils.rejectIfEmptyOrWhitespace
//						(errors, "stitd", "","簡介字數超過50請修改");
		}
		
	}
}
