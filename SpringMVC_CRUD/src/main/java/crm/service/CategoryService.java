package crm.service;

import java.util.List;
import crm.model.Category;
import crm.model.Hobby;

public interface CategoryService {
	List<Category> getAllCategories();
	Category getCategory(Integer id);
}
