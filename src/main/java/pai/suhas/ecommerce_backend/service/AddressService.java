package pai.suhas.ecommerce_backend.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pai.suhas.ecommerce_backend.dto.AddressRequest;
import pai.suhas.ecommerce_backend.dto.AddressResponse;
import pai.suhas.ecommerce_backend.entity.Address;
import pai.suhas.ecommerce_backend.entity.User;
import pai.suhas.ecommerce_backend.repository.AddressRepository;
import pai.suhas.ecommerce_backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService
{
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository,
                          UserRepository userRepository)
    {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public AddressResponse addAddress(AddressRequest request)
    {
        User user = getCurrentUser();

        if(Boolean.TRUE.equals(request.getDefaultAddress()))
        {
            Optional<Address> defaultAddress =
                    addressRepository.findByUserAndDefaultAddressTrue(user);

            if(defaultAddress.isPresent())
            {
                Address existing = defaultAddress.get();
                existing.setDefaultAddress(false);
                addressRepository.save(existing);
            }
        }

        Address address = new Address();

        address.setUser(user);
        address.setFullName(request.getFullName());
        address.setMobileNumber(request.getMobileNumber());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        address.setDefaultAddress(request.getDefaultAddress());

        Address savedAddress = addressRepository.save(address);

        return mapToResponse(savedAddress);
    }

    public List<AddressResponse> getAddresses()
    {
        User user = getCurrentUser();

        return addressRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void deleteAddress(Long id)
    {
        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        addressRepository.delete(address);
    }

    private AddressResponse mapToResponse(Address address)
    {
        AddressResponse response = new AddressResponse();

        response.setId(address.getId());
        response.setFullName(address.getFullName());
        response.setMobileNumber(address.getMobileNumber());
        response.setAddressLine1(address.getAddressLine1());
        response.setAddressLine2(address.getAddressLine2());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setPostalCode(address.getPostalCode());
        response.setCountry(address.getCountry());
        response.setDefaultAddress(address.getDefaultAddress());

        return response;
    }

    private User getCurrentUser()
    {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
}