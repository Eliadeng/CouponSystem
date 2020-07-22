package com.johnbryce.dao;

import com.johnbryce.beans.Category;

public interface CategoryDAO {
	int getCategoryID(Category category);

	Category getCategoryName(int ID);
}
