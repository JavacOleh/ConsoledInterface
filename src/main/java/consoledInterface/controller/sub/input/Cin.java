package consoledInterface.controller.sub.input;

import consoledInterface.controller.sub.output.OutputController;
import consoledInterface.util.ConcurrentUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cin {
    private final OutputController outputController;
    private boolean isCinActive = false;
    private String text;

    public Cin(OutputController outputController) {
        this.outputController = outputController;
        text = "";
    }

    public String getLine() {
        isCinActive = true; //Почему-то оно не всегда успевает установить здесь флажок.

        do {
            if(!isCinActive)
                break;
            else
                ConcurrentUtil.sleep();

        }while (true);

        return text;
    }
}
