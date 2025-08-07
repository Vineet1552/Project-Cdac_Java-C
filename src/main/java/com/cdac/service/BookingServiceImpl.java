//package com.cdac.service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.cdac.custom_exceptions.ResourceNotFoundException;
//import com.cdac.dao.BookingDao;
//import com.cdac.dao.PackageDao;
//import com.cdac.dao.UserDao;
//import com.cdac.dao.VehicleDao;
//import com.cdac.dao.WasherDao;
//import com.cdac.dto.BookingDto;
//import com.cdac.entities.BookingEntity;
//import com.cdac.entities.PackageEntity;
//import com.cdac.entities.UserEntity;
//import com.cdac.entities.VehicleEntity;
//import com.cdac.entities.WasherEntity;
//import com.cdac.security.JwtUtils;
//
//import lombok.AllArgsConstructor;
//
//@Service
//@Transactional
//@AllArgsConstructor
//public class BookingServiceImpl implements BookingService {
//
//    @Autowired
//    private BookingDao bookingDao;
//
//    @Autowired
//    private UserDao userDao;
//
//    @Autowired
//    private VehicleDao vehicleDao;
//
//    @Autowired
//    private WasherDao washerDao;
//
//    @Autowired
//    private PackageDao packageDao;
//    
//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @Override
//    public BookingDto createBooking(BookingDto dto, String userEmail) {
//        UserEntity user = userDao.findByEmail(userEmail)
//            .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));
//
//        VehicleEntity vehicle = vehicleDao.findById(dto.getVehicleId())
//                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "ID", dto.getVehicleId()));
//
//        WasherEntity washer = washerDao.findById(dto.getWasherId())
//                .orElseThrow(() -> new ResourceNotFoundException("Washer", "ID", dto.getWasherId()));
//
//        PackageEntity pkg = packageDao.findById(dto.getPackageId())
//                .orElseThrow(() -> new ResourceNotFoundException("Package", "ID", dto.getPackageId()));
//
//        BookingEntity entity = new BookingEntity();
//        entity.setUser(user);
//        entity.setVehicle(vehicle);
//        entity.setWasher(washer);
//        entity.setPackageEntity(pkg);
//        entity.setBookingDate(dto.getBookingDate());
//        entity.setStatus(dto.getStatus());
//        entity.setRemarks(dto.getRemarks());
//
//        BookingEntity saved = bookingDao.save(entity);
//        return mapToDto(saved);
//    }
//
//    @Override
//    public BookingDto getBookingById(Long id) {
//        BookingEntity entity = bookingDao.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Booking", "ID", id));
//        return mapToDto(entity);
//    }
//
//    @Override
//    public List<BookingDto> getAllBookings() {
//        return bookingDao.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public BookingDto updateBooking(Long id, BookingDto dto) {
//        BookingEntity entity = bookingDao.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Booking", "ID", id));
//
//        entity.setStatus(dto.getStatus());
//        entity.setRemarks(dto.getRemarks());
//
//        BookingEntity updated = bookingDao.save(entity);
//        return mapToDto(updated);
//    }
//
//    @Override
//    public void deleteBooking(Long id) {
//        BookingEntity entity = bookingDao.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Booking", "ID", id));
//        bookingDao.delete(entity);
//    }
//
//    @Override
//    public List<BookingDto> getBookingsByUserEmail(String email) {
//        UserEntity user = userDao.findByEmail(email)
//            .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
//        List<BookingEntity> bookings = bookingDao.findByUser(user);
//        return bookings.stream().map(this::mapToDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<BookingDto> getBookingsByWasherId(Long washerId) {
//        WasherEntity washer = washerDao.findById(washerId)
//            .orElseThrow(() -> new ResourceNotFoundException("Washer", "ID", washerId));
//        List<BookingEntity> bookings = bookingDao.findByWasher(washer);
//        return bookings.stream().map(this::mapToDto).collect(Collectors.toList());
//    }
//
//    private BookingDto mapToDto(BookingEntity entity) {
//        BookingDto dto = new BookingDto();
//       // dto.setId(entity.getId());
//        dto.setUserId(entity.getUser().getId());
//        dto.setVehicleId(entity.getVehicle().getId());
//        dto.setWasherId(entity.getWasher().getId());
//        dto.setPackageId(entity.getPackageEntity().getId());
//        dto.setBookingDate(entity.getBookingDate());
//        dto.setStatus(entity.getStatus());
//        dto.setRemarks(entity.getRemarks());
//        return dto;
//    }
//}
//


package com.cdac.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.custom_exceptions.ResourceNotFoundException;
import com.cdac.dao.BookingDao;
import com.cdac.dao.PackageDao;
import com.cdac.dao.UserDao;
import com.cdac.dao.VehicleDao;
import com.cdac.dao.WasherDao;
import com.cdac.dto.BookingCreateDto;
import com.cdac.dto.BookingDto;
import com.cdac.entities.BookingEntity;
import com.cdac.entities.PackageEntity;
import com.cdac.entities.UserEntity;
import com.cdac.entities.VehicleEntity;
import com.cdac.entities.WasherEntity;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

//    private final BookingDao bookingDao;
//    private final UserDao userDao;
//    private final VehicleDao vehicleDao;
//    private final WasherDao washerDao;
//    private final PackageDao packageDao;
	
	@Autowired
    private BookingDao bookingDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private PackageDao packageDao;

    @Autowired
    private WasherDao washerDao;

//    @Override
//    public BookingDto createBooking(BookingCreateDto createDto, String userEmail) {
//        // 1) find user
//        UserEntity user = userDao.findByEmail(userEmail)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));
//
//        // 2) find vehicle and check ownership
//        VehicleEntity vehicle = vehicleDao.findById(createDto.getVehicleId())
//                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "ID", createDto.getVehicleId()));
//
//        if (vehicle.getUser() == null || !vehicle.getUser().getId().equals(user.getId())) {
//            throw new RuntimeException("Vehicle does not belong to authenticated user");
//        }
//
//        // 3) find washer
//        WasherEntity washer = washerDao.findById(createDto.getWasherId())
//                .orElseThrow(() -> new ResourceNotFoundException("Washer", "ID", createDto.getWasherId()));
//
//        // 4) find package
//        PackageEntity pkg = packageDao.findById(createDto.getPackageId())
//                .orElseThrow(() -> new ResourceNotFoundException("Package", "ID", createDto.getPackageId()));
//
//        // 5) optional business checks (bookingAt in future etc) — add as needed
//        // e.g. if (createDto.getBookingAt().isBefore(LocalDateTime.now())) throw new RuntimeException(...);
//
//        // 6) create entity and snapshot price/name
//        BookingEntity entity = new BookingEntity();
//        entity.setUser(user);
//        entity.setVehicle(vehicle);
//        entity.setWasher(washer);
//        entity.setPackageEntity(pkg);
//        entity.setBookingAt(createDto.getBookingAt()); // LocalDateTime
//        entity.setStatus("PENDING");
//        entity.setRemarks(createDto.getRemarks());
//        entity.setPriceAtBooking(pkg.getPrice());     // snapshot
//        entity.setPackageNameAtBooking(pkg.getName()); // snapshot
//
//        BookingEntity saved = bookingDao.save(entity);
//        return mapToDto(saved);
//    }
    
    
    
    @Override
    public BookingDto createBooking(BookingCreateDto createDto, String userEmail) {
        UserEntity user = userDao.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        VehicleEntity vehicle = vehicleDao.findById(createDto.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + createDto.getVehicleId()));

        PackageEntity packageEntity = packageDao.findById(createDto.getPackageId())
                .orElseThrow(() -> new ResourceNotFoundException("Package not found with id: " + createDto.getPackageId()));

        WasherEntity washer = washerDao.findById(createDto.getWasherId())
                .orElseThrow(() -> new ResourceNotFoundException("Washer not found with id: " + createDto.getWasherId()));
        
        
     // Check for existing active bookings (PENDING or CONFIRMED) for the same user, package, and washer
        List<BookingEntity> existingBookings = bookingDao.findByUserIdAndPackageEntityIdAndWasherIdAndStatusIn(
                user.getId(), createDto.getPackageId(), createDto.getWasherId(), List.of("PENDING", "CONFIRMED"));
        if (!existingBookings.isEmpty()) {
            throw new IllegalStateException("You already have an active booking for this package with this washer.");
        }

        BookingEntity booking = new BookingEntity();
        booking.setBookingAt(createDto.getBookingAt());
//        booking.setBookingDate(createDto.getBookingAt().toLocalDate());
        booking.setPackageEntity(packageEntity);
        booking.setPackageNameAtBooking(packageEntity.getName());
        booking.setPriceAtBooking(packageEntity.getPrice());
        booking.setRemarks(createDto.getRemarks());
        booking.setStatus("PENDING");
        booking.setUser(user);
        booking.setVehicle(vehicle);
        booking.setWasher(washer);

        BookingEntity saved = bookingDao.save(booking);

        return entityToDto(saved);
    }
    
    private BookingDto entityToDto(BookingEntity entity) {
        BookingDto dto = new BookingDto();
        dto.setId(entity.getId());
        dto.setVehicleId(entity.getVehicle().getId());
        dto.setPackageId(entity.getPackageEntity().getId());
        dto.setWasherId(entity.getWasher().getId());
        dto.setUserId(entity.getUser().getId());
        dto.setBookingAt(entity.getBookingAt());
        dto.setRemarks(entity.getRemarks());
        dto.setStatus(entity.getStatus());
        return dto;
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
    public BookingDto updateBooking(Long id, BookingDto bookingDto) {
        BookingEntity entity = bookingDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "ID", id));

        // Only allow certain fields to be updated here (status and remarks).
        if (bookingDto.getStatus() != null) entity.setStatus(bookingDto.getStatus());
        if (bookingDto.getRemarks() != null) entity.setRemarks(bookingDto.getRemarks());

        BookingEntity updated = bookingDao.save(entity);
        return mapToDto(updated);
    }

    @Override
    public void deleteBooking(Long id) {
        BookingEntity entity = bookingDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "ID", id));
        bookingDao.delete(entity);
    }

    @Override
    public List<BookingDto> getBookingsByUserEmail(String email) {
        UserEntity user = userDao.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return bookingDao.findByUser(user).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getBookingsByWasherId(Long washerId) {
        WasherEntity washer = washerDao.findById(washerId)
                .orElseThrow(() -> new ResourceNotFoundException("Washer", "ID", washerId));
        return bookingDao.findByWasher(washer).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // --- mapping helper
    private BookingDto mapToDto(BookingEntity entity) {
        BookingDto dto = new BookingDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser() != null ? entity.getUser().getId() : null);
        dto.setVehicleId(entity.getVehicle() != null ? entity.getVehicle().getId() : null);
        dto.setWasherId(entity.getWasher() != null ? entity.getWasher().getId() : null);
        dto.setPackageId(entity.getPackageEntity() != null ? entity.getPackageEntity().getId() : null);
        dto.setBookingAt(entity.getBookingAt()); // LocalDateTime
        dto.setStatus(entity.getStatus());
        dto.setRemarks(entity.getRemarks());
        // Optional: include snapshots if your BookingDto has them
        // dto.setPriceAtBooking(entity.getPriceAtBooking());
        // dto.setPackageNameAtBooking(entity.getPackageNameAtBooking());
        return dto;
    }
}
