package xun.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import xun.model.ProductInfoBean;

@Component
public class ProductVaildators implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return ProductInfoBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProductInfoBean pib = (ProductInfoBean)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "product_name", "","商品名稱不能為空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "product_price", "","商品價格不能為空白");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "product_quantity", "","商品庫存不能為空白");
		if (pib.getProduct_price()<0) {
			errors.rejectValue("product_price", "", "商品價格不能為負數");
		}
		if(pib.getProduct_quantity()<0) {
			errors.rejectValue("product_quantity", "","商品庫存不能為負數");
		}
		
	}
}
