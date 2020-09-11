const ModalApp = {};
ModalApp.ModalProcess = function (parameters) {
    this.id = parameters['id'] || 'modal';
    this.selector = parameters['selector'] || '';
    this.body = parameters['body'] || '';
    this.content = '<div id="' + this.id + '" class="modal fade" tabindex="-1" role="dialog">' +
        '<div class="modal-dialog modal-custom" role="document">' +
        '<div class="modal-content">' +
        '<div class="modal-body">' + this.body + '</div>' +
        '</div>' +
        '</div>';
    this.init = function () {
        if ($('#' + this.id).length == 0) {
            $('body').prepend(this.content);
        }
        if (this.selector) {
            $(document).on('click', this.selector, $.proxy(this.showModal, this));
        }
    }
};
ModalApp.ModalProcess.prototype.changeBody = function (content) {
    $('#' + this.id + ' .modal-body').html(content);
};
ModalApp.ModalProcess.prototype.showModal = function () {
    $('#' + this.id).modal('show');
};
ModalApp.ModalProcess.prototype.hideModal = function () {
    $('#' + this.id).modal('hide');
};
ModalApp.ModalProcess.prototype.updateModal = function () {
    $('#' + this.id).modal('handleUpdate');
};


$(function () {
    const modal = new ModalApp.ModalProcess({id: 'modal'});
    modal.init();
    $('.modal-show').click(function () {
        modal.changeBody($(this).attr('data-content'));
        modal.showModal();
    });
    $('#' + modal.id).on('hidden.bs.modal', function () {
        modal.changeBody('');
    });
});
