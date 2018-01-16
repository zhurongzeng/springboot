package com.chu.readinglist.service;

import com.chu.readinglist.dao.ReaderDAO;
import com.chu.readinglist.po.Reader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReaderService implements UserDetailsService {
    @Autowired
    private ReaderDAO readerDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Reader reader = readerDAO.findByUsername(username);
        if (reader == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        return reader;
    }
}
