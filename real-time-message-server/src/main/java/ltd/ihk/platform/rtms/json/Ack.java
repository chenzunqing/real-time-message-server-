package ltd.ihk.platform.rtms.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jingyk
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ack {
    private boolean success;
}
