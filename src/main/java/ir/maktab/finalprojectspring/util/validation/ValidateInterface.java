package ir.maktab.finalprojectspring.util.validation;


import ir.maktab.finalprojectspring.exception.InvalidInputException;

@FunctionalInterface
public interface ValidateInterface {

    void accept(String input, String regex, String errorMsg) throws InvalidInputException;
}
