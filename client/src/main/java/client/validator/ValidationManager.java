package client.validator;
import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;

import javafx.scene.Node;

/**
 * Менеджер для валидации
 */
public class ValidationManager {
    /**
     * Добавляет валидатор
     * @param attachListener - слушатель
     * @param validator - валидатор
     * @param nodes - узлы
     */
    public static void addValidator(Boolean attachListener, ValidatorBase validator, IFXValidatableControl... nodes) {
        for (IFXValidatableControl node : nodes) {
            node.getValidators().add(validator);
            if (attachListener) {
                addFocusListener(node);
            }
        }
    }

    /**
     * Добавляет слушателя
     * @param node - узлы
     */
    private static void addFocusListener(IFXValidatableControl node) {
        ((Node) node).focusedProperty().addListener((observable, oldValue, newValue) -> {
            node.resetValidation();
            if (!newValue) {
                node.validate();
            }
        });
    }
}
