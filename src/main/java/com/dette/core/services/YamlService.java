package com.dette.core.services;

import java.util.Map;

public interface YamlService {
    Map<String, Object> loadYaml();

    Map<String, Object> loadYaml(String path);
}
