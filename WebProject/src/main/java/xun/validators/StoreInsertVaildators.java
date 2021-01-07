package xun.validators;

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
		System.out.println("使用validate");
		System.out.println(sb.getStitd());
		if(sb.getStitd().length()>49) {
			System.out.println("簡介超過50字 應該要擋下來++++++++++++++++++++++++++++++++++++");
			errors.rejectValue("stitd","", "簡介字數超過50請修改");
//			ValidationUtils.rejectIfEmptyOrWhitespace
//						(errors, "stitd", "","簡介字數超過50請修改");
		}
		
	}
}
