package pai.suhas.ecommerce_backend.controller;

import org.springframework.web.bind.annotation.*;
import pai.suhas.ecommerce_backend.dto.AddressRequest;
import pai.suhas.ecommerce_backend.dto.AddressResponse;
import pai.suhas.ecommerce_backend.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController
{
    private final AddressService addressService;

    public AddressController(AddressService addressService)
    {
        this.addressService = addressService;
    }

    @PostMapping
    public AddressResponse addAddress(@RequestBody AddressRequest request)
    {
        return addressService.addAddress(request);
    }

    @GetMapping
    public List<AddressResponse> getAddresses()
    {
        return addressService.getAddresses();
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id)
    {
        addressService.deleteAddress(id);
    }
}