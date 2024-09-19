package avada.spacelab.kino_cms.controller.paged;

import java.util.List;

public record PagedResponse<T>(
        List<T> content,
        int pageSize,
        int pageNumber,
        int pageAmount,
        int elementAmount,
        boolean hasPrevious,
        boolean hasNext,
        boolean isFirst,
        boolean isLast
) {
    public static <T> PagedResponse<T> Of(
            List<T> items,
            int pageSize,
            int pageNumber,
            int pageAmount,
            int elementAmount,
            boolean hasPrevious,
            boolean hasNext,
            boolean isFirst,
            boolean isLast
    ) {
        return new PagedResponse<>(
                items,
                pageSize,
                pageNumber,
                pageAmount,
                elementAmount,
                hasPrevious,
                hasNext,
                isFirst,
                isLast
        );
    }
}
