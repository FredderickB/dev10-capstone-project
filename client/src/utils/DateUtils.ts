export const formatDate = (dateString: string | null | undefined): string => {
    if (!dateString) return 'N/A'

    const date = new Date(dateString)
    if (isNaN(date.getTime())) return dateString // Fallback if string is invalid

    // Display formatted local date & time (e.g., "Sep 1, 2026, 3:54 AM")
    return date.toLocaleString(undefined, {
      dateStyle: 'medium',
      timeStyle: 'short',
    })
  }