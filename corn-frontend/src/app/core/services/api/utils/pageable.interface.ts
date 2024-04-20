export interface Pageable<T> {
    page?: number;
    size?: number;
    sort?: string;
    phony: T;
}

function of<T>(page?: number, size?: number, sortBy?: keyof T, direction?: 'ASC' | 'DESC'): Pageable<T> {
    const pageable: Pageable<T> = {} as Pageable<T>;
    if (page !== undefined) {
        pageable.page = page;
    }
    if (size !== undefined) {
        pageable.size = size;
    }
    if (sortBy !== undefined) {
        pageable.sort = sortBy as string;
        if (direction !== undefined) {
            pageable.sort += "," + direction;
        }
    }
    return pageable;
}

export const Pageable = {
    of: of
};