package com.cdac.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.custom_exceptions.ResourceNotFoundException;
import com.cdac.dao.BookingDao;
import com.cdac.dao.PackageDao;
import com.cdac.dao.PaymentDao;
import com.cdac.dao.UserDao;
import com.cdac.dao.VehicleDao;
import com.cdac.dao.WasherDao;
import com.cdac.dto.PaymentDto;
import com.cdac.entities.BookingEntity;
import com.cdac.entities.PaymentEntity;
import com.cdac.entities.UserEntity;
import com.cdac.security.JwtUtils;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private BookingDao bookingDao;
    
    @Autowired
    private UserDao userDao;

    @Override
    public PaymentDto createPayment(PaymentDto dto, String userEmail) {
        // Find user by email
        UserEntity user = userDao.findByEmail(userEmail)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        // Find booking and ensure it's linked to this user
        BookingEntity booking = bookingDao.findById(dto.getBookingId())
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + dto.getBookingId()));

        if (!booking.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized: Booking does not belong to this user");
        }

        // Proceed to create payment
        PaymentEntity entity = new PaymentEntity();
        entity.setAmount(dto.getAmount());
        entity.setPaymentDate(LocalDateTime.now());
        entity.setPaymentMethod(dto.getPaymentMethod());
        entity.setPaymentStatus(dto.getPaymentStatus());
        entity.setBooking(booking);

        PaymentEntity saved = paymentDao.save(entity);

        dto.setId(saved.getId());
        dto.setPaymentDate(saved.getPaymentDate().toString());
        return dto;
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return paymentDao.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public PaymentDto getPaymentById(Long id) {
        PaymentEntity payment = paymentDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        return mapToDto(payment);
    }

    @Override
    public void deletePayment(Long id) {
        PaymentEntity payment = paymentDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        paymentDao.delete(payment);
    }

    private PaymentDto mapToDto(PaymentEntity entity) {
        PaymentDto dto = new PaymentDto();
        dto.setId(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setBookingId(entity.getBooking().getId());
        dto.setPaymentDate(entity.getPaymentDate().toString());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setPaymentStatus(entity.getPaymentStatus());
        return dto;
    }
}