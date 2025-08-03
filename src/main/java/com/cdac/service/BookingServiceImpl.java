package com.cdac.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.dao.BookingDao;
import com.cdac.dao.PackageDao;
import com.cdac.dao.UserDao;
import com.cdac.dao.VehicleDao;
import com.cdac.dao.WasherDao;
import com.cdac.dto.BookingDto;
import com.cdac.entities.BookingEntity;
import com.cdac.entities.PackageEntity;
import com.cdac.entities.UserEntity;
import com.cdac.entities.VehicleEntity;
import com.cdac.entities.WasherEntity;
import com.cdac.custom_exceptions.ResourceNotFoundException;
import com.cdac.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private WasherDao washerDao;

    @Autowired
    private PackageDao packageDao;

    @Override
    public BookingDto createBooking(BookingDto dto) {
        BookingEntity entity = new BookingEntity();

        UserEntity user = userDao.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", dto.getUserId()));
        VehicleEntity vehicle = vehicleDao.findById(dto.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "ID", dto.getVehicleId()));
        WasherEntity washer = washerDao.findById(dto.getWasherId())
                .orElseThrow(() -> new ResourceNotFoundException("Washer", "ID", dto.getWasherId()));
        PackageEntity pkg = packageDao.findById(dto.getPackageId())
                .orElseThrow(() -> new ResourceNotFoundException("Package", "ID", dto.getPackageId()));

        entity.setUser(user);
        entity.setVehicle(vehicle);
        entity.setWasher(washer);
        entity.setPackageEntity(pkg);
//        entity.setBookingDate(LocalDateTime.parse(dto.getBookingDate()));
        entity.setBookingDate(dto.getBookingDate());
        entity.setStatus(dto.getStatus());
        entity.setRemarks(dto.getRemarks());
        
        BookingEntity saved = bookingDao.save(entity);
//        dto.setId(saved.getId());

//        return dto;
        return mapToDto(saved);
    }

    @Override
    public BookingDto getBookingById(Long id) {
        BookingEntity entity = bookingDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "ID", id));

        return mapToDto(entity);
    }

    @Override
    public List<BookingDto> getAllBookings() {
        return bookingDao.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public BookingDto updateBooking(Long id, BookingDto dto) {
        BookingEntity entity = bookingDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "ID", id));

        entity.setStatus(dto.getStatus());
        entity.setRemarks(dto.getRemarks());

        BookingEntity updated = bookingDao.save(entity);
        return mapToDto(updated);
    }

    @Override
    public void deleteBooking(Long id) {
        BookingEntity entity = bookingDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "ID", id));
        bookingDao.delete(entity);
    }

    private BookingDto mapToDto(BookingEntity entity) {
        BookingDto dto = new BookingDto();
//        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setVehicleId(entity.getVehicle().getId());
        dto.setWasherId(entity.getWasher().getId());
        dto.setPackageId(entity.getPackageEntity().getId());
        dto.setBookingDate(entity.getBookingDate());
        
        dto.setStatus(entity.getStatus());
        dto.setRemarks(entity.getRemarks());
        return dto;
    }
}