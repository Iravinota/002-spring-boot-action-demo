package com.ws.exp.sba.service;

import com.ws.exp.sba.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ReaderService
 *
 * @author Eric at 2020-04-19_15:55
 */
public interface ReaderService extends JpaRepository<Reader, String> {
}
