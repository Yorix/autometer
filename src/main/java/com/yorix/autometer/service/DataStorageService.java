package com.yorix.autometer.service;

import java.io.IOException;

public interface DataStorageService {

    void save();

    void read() throws IOException;
}
