document.addEventListener('DOMContentLoaded', function () {
    const consumerIdInput = document.querySelector('input[name="consumerId"]');
    if (consumerIdInput) {
        consumerIdInput.addEventListener('input', function () {
            const value = this.value.trim();
            if (!value) {
                return;
            }
            if (value.length < 3) {
                this.setCustomValidity('Consumer ID must be at least 3 characters.');
            } else {
                this.setCustomValidity('');
            }
        });
    }
});
