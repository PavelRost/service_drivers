package com.example.service_driver.service;

import com.example.service_driver.entities.Driver;
import com.example.service_driver.repository.DriverRepository;
import org.springframework.stereotype.Service;

@Service
public class MoneyService {

    private static final double RED_TO_GREEN = 2.5;
    private static final double GREEN_TO_BLUE = 0.6;

    private final DriverRepository driverRepository;

    public MoneyService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public boolean addRedMoney(int idDriver, int addMoney) {
        if (addMoney == 0 || addMoney < 0) return false;
        boolean rsl = false;
        Driver driver = driverRepository.findById(idDriver).orElse(null);
        if (driver != null) {
            double pastValueMoney = driver.getBlueMoney();
            double newValueMoney = addMoney * RED_TO_GREEN / GREEN_TO_BLUE;
            driver.setBlueMoney(newValueMoney + pastValueMoney);
            driverRepository.save(driver);
            rsl = true;
        }
        return rsl;
    }

    public boolean addGreenMoney(int idDriver, int addMoney) {
        if (addMoney == 0 || addMoney < 0) return false;
        boolean rsl = false;
        Driver driver = driverRepository.findById(idDriver).orElse(null);
        if (driver != null) {
            double pastValueMoney = driver.getBlueMoney();
            double newValueMoney = addMoney / GREEN_TO_BLUE;
            driver.setBlueMoney(newValueMoney + pastValueMoney);
            driverRepository.save(driver);
            rsl = true;
        }
        return rsl;
    }

    public boolean addBlueMoney(int idDriver, int addMoney) {
        if (addMoney == 0 || addMoney < 0) return false;
        boolean rsl = false;
        Driver driver = driverRepository.findById(idDriver).orElse(null);
        if (driver != null) {
            double pastValueMoney = driver.getBlueMoney();
            driver.setBlueMoney(addMoney + pastValueMoney);
            driverRepository.save(driver);
            rsl = true;
        }
        return rsl;
    }

    public boolean getRedMoney(int idDriver, int getMoney) {
        boolean rsl = false;
        Driver driver = driverRepository.findById(idDriver).orElse(null);
        if (driver != null) {
            double currentMoney = driver.getBlueMoney();
            double takeMoney = getMoney * RED_TO_GREEN / GREEN_TO_BLUE;
            if (isEnoughMoneyInAccount(currentMoney, takeMoney)) {
                driver.setBlueMoney(currentMoney - takeMoney);
                driverRepository.save(driver);
                rsl = true;
            }
        }
        return rsl;
    }

    public boolean getGreenMoney(int idDriver, int getMoney) {
        boolean rsl = false;
        Driver driver = driverRepository.findById(idDriver).orElse(null);
        if (driver != null) {
            double currentMoney = driver.getBlueMoney();
            double takeMoney = getMoney / GREEN_TO_BLUE;
            if (isEnoughMoneyInAccount(currentMoney, takeMoney)) {
                driver.setBlueMoney(currentMoney - takeMoney);
                driverRepository.save(driver);
                rsl = true;
            }
        }
        return rsl;
    }

    public boolean getBlueMoney(int idDriver, int getMoney) {
        boolean rsl = false;
        Driver driver = driverRepository.findById(idDriver).orElse(null);
        if (driver != null) {
            double currentMoney = driver.getBlueMoney();
            if (isEnoughMoneyInAccount(currentMoney, getMoney)) {
                driver.setBlueMoney(currentMoney - getMoney);
                driverRepository.save(driver);
                rsl = true;
            }
        }
        return rsl;
    }

    public double showBlueMoney(int idDriver) {
        Driver driver = driverRepository.findById(idDriver).orElse(null);
        return driver != null ? driver.getBlueMoney() : -1.0;
    }

    public double showGreenMoney(int idDriver) {
        Driver driver = driverRepository.findById(idDriver).orElse(null);
        return driver != null ? driver.getBlueMoney() * GREEN_TO_BLUE : -1.0;
    }

    public double showRedMoney(int idDriver) {
        Driver driver = driverRepository.findById(idDriver).orElse(null);
        return driver != null ? driver.getBlueMoney() * GREEN_TO_BLUE / RED_TO_GREEN : -1.0;
    }

    private boolean isEnoughMoneyInAccount(double currentMoney, double takeMoney) {
        return currentMoney >= takeMoney;
    }
}
