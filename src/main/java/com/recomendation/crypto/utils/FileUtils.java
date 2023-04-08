package com.recomendation.crypto.utils;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.recomendation.crypto.model.Currency;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Log4j2
public class FileUtils {

    private static final String CURRENCY_FOLDER = "currencies";

    private File[] getResourceFolderFiles() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(FileUtils.CURRENCY_FOLDER);
        String path = Objects.requireNonNull(url).getPath();
        return new File(path).listFiles();
    }

    /**
     * Retrieves all the currencies in the 'currencies' folder
     * Able to add as many currencies as required
     */
    public List<List<Currency>> retrieveCurrencies() {
        File[] files = getResourceFolderFiles();
        List<List<Currency>> currencies = new ArrayList<>();
        if (files.length != 0) {
            for (File file : files) {
                try (Reader reader = new BufferedReader(new FileReader(file))) {
                    CsvToBean<Currency> csvToBean = new CsvToBeanBuilder(reader)
                            .withType(Currency.class)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();
                    currencies.add(csvToBean.parse());
                } catch (Exception ex) {
                    log.error("Unable to parse files: " + ex.getLocalizedMessage());
                }
            }
        }
        return currencies;
    }
}