package dev.corn.cornbackend.entities.sprint.interfaces;

import java.time.LocalDate;

/**
 * Interface for date validation in Sprint entity
 */
public interface DateController {
    /**
     * Check if start date is before given date
     *
     * @param date date to compare
     * @return true if start date is before given date
     */
    boolean isStartBefore(LocalDate date);

    /**
     * Check if start date is after given date
     *
     * @param date date to compare
     * @return true if start date is after given date
     */
    boolean isStartAfter(LocalDate date);

    /**
     * Check if end date is before given date
     *
     * @param date date to compare
     * @return true if end date is before given date
     */
    boolean isEndBefore(LocalDate date);

    /**
     * Check if end date is after given date
     *
     * @param date date to compare
     * @return true if end date is after given date
     */
    boolean isEndAfter(LocalDate date);
}
