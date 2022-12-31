package Sam;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.opencsv.CSVWriter;

public class Scrapbookmyshow
{
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static String url="https://in.bookmyshow.com/";
	public static String city_path="//*[@id='modal-root']/div/div/div/div[2]/ul/li[3]/div/div/img";
	public static String movie_path="//div[.='Once Upon a Time in Jamaligudda']/div";
	public static String theatres_path="//div[@class='listing-info']/div[2]/div/div/a";
	public static String timings_path="//div[@class='body showtimes-details-container ']/div/div/a/div/div";
	
	
	@Test
	public static void open_Browser()
	{
		System.setProperty("webdriver.chrome.driver", "./Software/chromedriver.exe");
		/*ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		driver=new ChromeDriver(options);
		*/
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		wait= new WebDriverWait(driver,20);
		
		driver.get(url);
		
	}
	@Test(priority=1)
	public static void scrap_movies() throws InterruptedException, IOException
	{
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(city_path)))).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(movie_path)))).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='page-cta-container']/button/div/span")).click();
		Thread.sleep(10000);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[.='Not Now']")))).click();
		List<WebElement> theatres = driver.findElements(By.xpath(theatres_path));
		Thread.sleep(2000);
		List<WebElement> timings = driver.findElements(By.xpath(timings_path));
		int no = theatres.size();
		System.out.println(no);
		CSVWriter writer= new CSVWriter(new FileWriter("csvwrite//movies1.csv"));
		String[]set1= {"Theatre Name","Show Timings"};
		writer.writeNext(set1);
		writer.flush();
		for(int i=0;i<no;i++)
		{
			
			
			String theatre = theatres.get(i).getText();
			System.out.println("Theatre name is "+ theatre);
			String time = timings.get(i).getText();
			System.out.println(time);
			String[] set2= {theatre,time};
			writer.writeNext(set2);
			writer.flush();
			
			
		}

		
		
	}


}
