package client.validator;
import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;

/**
 * Вспомогательный валидатор
 */
public class CustomValidator extends ValidatorBase{
    public CustomValidator(String message) {
        super(message);
    }

    @Override
    protected void eval() {
    }


    public void showMessage() {
        this.hasErrors.set(true);
        ((IFXValidatableControl) getSrcControl()).validate();
        this.hasErrors.set(false);
    }
}
