package com.zerobank.pages;

import com.zerobank.utilities.BrowserUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FindTransactionsPage extends BasePage {

    @FindBy(css = "#aa_description")
    @CacheLookup
    private WebElement descriptionInputBox;

    @FindBy(xpath = "//button[@type='submit']")
    @CacheLookup
    private WebElement findButton;

    @FindBy(xpath = "//div[@id='filtered_transactions_for_account']//tbody/tr/td[2]")
    @CacheLookup
    private List<WebElement> descriptions;

    @FindBy(css = "#aa_fromDate")
    @CacheLookup
    private WebElement fromDateBox;

    @FindBy(css = "#aa_toDate")
    @CacheLookup
    private WebElement toDateBox;

    @FindBy(xpath = "//div[@id='filtered_transactions_for_account']//td[1]")
    @CacheLookup
    private List<WebElement> dates;

    public WebElement getDescriptionInputBox() {
        return descriptionInputBox;
    }

    public void tapOnFindButton() {
        BrowserUtils.clickWithJS(findButton);
    }

    //First Way
    public List<String> getAllTextsFromDescriptions() {
        return BrowserUtils.getElementsText(descriptions);
    }

    public void fillDateBoxes(String fromDate, String toDate) {
        BrowserUtils.sendKeys(fromDateBox, fromDate);
        BrowserUtils.sendKeys(toDateBox, toDate);
    }

    public void verifyDatesRange(String fromDate, String toDate) throws ParseException {
        List<String> stringOfDates = BrowserUtils.getElementsText(dates);
        List<Date> datesOfTransactions = new ArrayList<>();
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (String stringOfDate : stringOfDates) {
            Date date = sdFormat.parse(stringOfDate);
            datesOfTransactions.add(date);
        }
        Date fromdate=sdFormat.parse(fromDate);
        Date todate=sdFormat.parse(toDate);

        for (Date datesOfTransaction : datesOfTransactions) {
            Assert.assertTrue(datesOfTransaction.compareTo(fromdate) >= 0 && datesOfTransaction.compareTo(todate) <= 0);

        }
        }

        public boolean isDatesSorted () throws ParseException {
            List<String> stringOfDates = BrowserUtils.getElementsText(dates);
            List<Date> datesOfTransactions = new ArrayList<>();
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (String stringOfDate : stringOfDates) {
                Date date = sdFormat.parse(stringOfDate);
                datesOfTransactions.add(date);
            }

            for (int i = 0; i < datesOfTransactions.size(); i++) {
                for (int j = i+1; j < datesOfTransactions.size(); j++) {
                    if (datesOfTransactions.get(i).compareTo(datesOfTransactions.get(j)) <= 0) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

