package io.hashimati.myresturantordersys.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.hashimati.myresturantordersys.domains.CreationStatus;
import io.hashimati.myresturantordersys.domains.Menu;
import io.hashimati.myresturantordersys.domains.MenuItem;
import io.hashimati.myresturantordersys.repository.MenuRepository;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.reactivex.Single;

/**
 * @author Ahmed Al Hashmi @hashimati
 * MenuService
 */
@Singleton
public class MenuService {

    @Inject
    private MenuRepository menuRepository; 

	public Single<Menu> save(Menu menu) {
		return menuRepository.save(menu); 

	}

	public Single<Menu> findById(String id)
	{
		return menuRepository.findById(id); 
	}
	public Single<String> addItem(MenuItem menuItem, String id) {
		Menu menu = findById(id).blockingGet();
		
		boolean result = menu.getItems().add(menuItem); 

		System.out.println(result); 
		return menuRepository.update(menu); 
	}
	public Single<String> deleteItemByIndex(String id, int index)
	{
		Menu menu = findById(id).blockingGet();
		menu.getItems().remove(index); 
		return menuRepository.update(menu); 

	}
	public Single<Boolean> deleteById(String id) {
		return menuRepository.deleteById(id);
	}

	public Single<Menu> createMenu(String username, String restaurant, CompletedFileUpload file) throws IOException {


		try{
			Menu menu = new Menu(); 
			HashSet<MenuItem> menuItems = new HashSet<MenuItem>(); 
			
			Workbook workbook = file.getName().toLowerCase().contains(ExcelExtension.XLS.toString().toLowerCase())?
			
			new  HSSFWorkbook(file.getInputStream()): new XSSFWorkbook(file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0); 
			sheet.forEach(r-> {

				if(r.getRowNum() > 0)
				{
						MenuItem menuItem = new MenuItem(); 

						menuItem.setItemName(r.getCell(0).getStringCellValue()); 
						menuItem.setDescription(r.getCell(1).getStringCellValue()); 
						menuItem.setPrice(r.getCell(2).getNumericCellValue());
						menuItem.setCategory(r.getCell(3).getStringCellValue());
						menuItems.add(menuItem); 
				}
			});
			menu.setRestaurant(restaurant);
			menu.setUsername(username);
			menu.setId(restaurant);
			menu.setItems(menuItems);
			return save(menu); 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return Single.just(new Menu(){{
				setCreationStatus(CreationStatus.FAILED);
			}}); 
		}
	}
	public static enum ExcelExtension{
        XLS, XLSX;
    }

}