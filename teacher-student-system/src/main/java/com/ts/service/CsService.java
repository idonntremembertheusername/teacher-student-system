package com.ts.service;

import com.ts.entity.Cs;
import com.ts.mapper.CsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CsService {
    @Autowired
    private CsMapper csMapper;
}
