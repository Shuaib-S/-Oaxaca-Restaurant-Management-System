class NotificationManager {
    constructor() {
        this.notificationDropdown = document.querySelector('.notification-dropdown');
        this.notificationMenu = document.querySelector('.notification-menu');
        this.notificationCountElement = document.querySelector('.notification-count')

        this.notificationCount = 0;
        this.lastChecked = null;
        this.pollInterval = 1000;
        this.startPolling();
        this.staffType = this.determineStaffType();
    }

    startPolling() {
        this.fetchNotifications();

        setInterval(() => {
            this.fetchNotifications();
        }, this.pollInterval);
    }

    determineStaffType() {
        const path = window.location.pathname;

        if (path.includes('/waiters-manage.html')) return 'waiter';
        if (path.includes('/kitchen.html')) return 'kitchen';

        return 'unknown';
    }

    async fetchNotifications() {
        try {
            const response = await fetch(`/api/notifications/get?staffType=${this.staffType}`);

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const notifications = await response.json();
            const count = notifications.length;

            if (count !== this.notificationCount) {
                this.updateNotificationBadge(count);
                this.notificationCount = count;
            }

            this.renderNotifications(notifications);
        } catch (error) {
            console.error('Failed to fetch notifications', error);
        }
    }

    renderNotifications(notifications){
        this.notificationMenu.innerHTML = '';

        notifications.forEach(notification => {
            const notificationItem = this.createNotificationElement(notification);
            this.notificationMenu.appendChild(notificationItem);
        });
    }

    createNotificationElement(notification) {
        const div = document.createElement('div');
        div.classList.add('notification-item');
        div.innerHTML = `
        <p>${notification.message}</p>
        <small>${this.formatTimeAgo(notification.createdAt)}</small>
        `;

        div.addEventListener('click', () => this.markNotificationAsRead(notification.id));

        return div;
    }

    async markNotificationAsRead(notificationId) {
        try {
            await fetch(`/api/notifications/${notificationId}/read`, { method: 'POST' });
            await this.fetchNotifications(); // Refresh notifications
        } catch (error) {
            console.error('Failed to mark notification as read', error);
        }
    }

    formatTimeAgo(dateString) {
        const date = new Date(dateString);
        const now = new Date();
        const diffInMinutes = Math.round((now - date) / (1000 * 60));

        if (diffInMinutes < 1) return 'Just now';
        if (diffInMinutes < 60) return `${diffInMinutes} mins ago`;

        return dateString;
    }

    updateNotificationBadge(count) {
        if (this.notificationCountElement) {
            this.notificationCountElement.textContent = count;
            this.notificationCountElement.style.display = count > 0 ? 'block' : 'none';
        }
    }
}

document.addEventListener('DOMContentLoaded', () => {
    new NotificationManager();
})
