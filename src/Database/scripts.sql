-- Create the HRMSystem database if it doesn't exist
CREATE DATABASE IF NOT EXISTS HRMSystem;
USE HRMSystem;

-- Personal information table
CREATE TABLE CONNGUOI (
    CMND VARCHAR(20) PRIMARY KEY,
    hoTen VARCHAR(100) NOT NULL,
    gioiTinh VARCHAR(10),
    ngaySinh DATE,
    diaChi VARCHAR(255),
    SDT VARCHAR(20),
    tinhTrangHonNhan VARCHAR(20),
    danToc VARCHAR(50),
    tonGiao VARCHAR(50),
    email VARCHAR(100)
);

-- Identity card information
CREATE TABLE CMND (
    soCMND VARCHAR(20) PRIMARY KEY,
    noiCap VARCHAR(100),
    ngayCap DATE
);

-- Educational qualifications
CREATE TABLE TRINHDO (
    maTrinhDo VARCHAR(20) PRIMARY KEY,
    trinhDoHocVan VARCHAR(100),
    trinhDoChuyenMon VARCHAR(100),
    chuyenNganh VARCHAR(100)
);

-- Company positions
CREATE TABLE CHUCVUCONGTY (
    maChucVu VARCHAR(20) PRIMARY KEY,
    tenChucVu VARCHAR(100),
    phuCapChucVu DECIMAL(18,2)
);

-- Job positions
CREATE TABLE CHUCVU (
    maChucVu VARCHAR(20) PRIMARY KEY,
    tenChucVu VARCHAR(100),
    phuCapChucVu DECIMAL(18,2),
    ngayNhanChuc DATE
);

-- User accounts
CREATE TABLE NHOMQUYEN (
    maNhomQuyen VARCHAR(20) PRIMARY KEY,
    tenNhomQuyen VARCHAR(100)
);

CREATE TABLE TAIKHOAN (
    username VARCHAR(50) PRIMARY KEY,
    pass VARCHAR(50) NOT NULL,
    maNhomQuyen VARCHAR(20),
    avatar VARCHAR(255),
    FOREIGN KEY (maNhomQuyen) REFERENCES NHOMQUYEN(maNhomQuyen)
);

CREATE TABLE CHITIETNHOMQUYEN (
    maNhomQuyen VARCHAR(20),
    maChucNang INT,
    PRIMARY KEY (maNhomQuyen, maChucNang),
    FOREIGN KEY (maNhomQuyen) REFERENCES NHOMQUYEN(maNhomQuyen)
);

-- Departments
CREATE TABLE PHONGBAN (
    maPhong VARCHAR(20) PRIMARY KEY,
    tenPhong VARCHAR(100) NOT NULL,
    ngayThanhLap DATE,
    maTruongPhong VARCHAR(20)
);

-- Labor contracts
CREATE TABLE HOPDONGLAODONG (
    maHopDong VARCHAR(20) PRIMARY KEY,
    tuNgay DATE,
    denNgay DATE,
    loaiHopDong VARCHAR(50),
    luongCoBan DECIMAL(18,2)
);

-- Employees
CREATE TABLE NHANVIEN (
    maNhanVien VARCHAR(20) PRIMARY KEY,
    CMND VARCHAR(20),
    maPhong VARCHAR(20),
    maTrinhDo VARCHAR(20),
    maChucVu VARCHAR(20),
    maHopDong VARCHAR(20),
    ngayBatDauThuViec DATE,
    ngayKetThucThuViec DATE,
    luongThuViec DECIMAL(18,2),
    trangThai INT DEFAULT 1,
    FOREIGN KEY (CMND) REFERENCES CONNGUOI(CMND),
    FOREIGN KEY (maPhong) REFERENCES PHONGBAN(maPhong),
    FOREIGN KEY (maTrinhDo) REFERENCES TRINHDO(maTrinhDo),
    FOREIGN KEY (maChucVu) REFERENCES CHUCVU(maChucVu),
    FOREIGN KEY (maHopDong) REFERENCES HOPDONGLAODONG(maHopDong)
);

-- Update the department table to reference manager
ALTER TABLE PHONGBAN
ADD CONSTRAINT FK_PHONGBAN_NHANVIEN FOREIGN KEY (maTruongPhong) REFERENCES NHANVIEN(maNhanVien);

-- Timesheet
CREATE TABLE BANGCHAMCONG (
    maBangChamCong VARCHAR(20) PRIMARY KEY,
    maNhanVien VARCHAR(20),
    thangChamCong INT,
    namChamCong INT,
    soNgayLamViec INT,
    soNgayNghi INT,
    soNgayTre INT,
    soGioLamThem INT,
    chiTiet LONGTEXT,
    FOREIGN KEY (maNhanVien) REFERENCES NHANVIEN(maNhanVien)
);

-- Salary
CREATE TABLE LUONG (
    maLuong VARCHAR(20) PRIMARY KEY,
    maBangChamCong VARCHAR(20),
    luongThucTe DECIMAL(18,2),
    luongThuong DECIMAL(18,2),
    phuCapChucVu DECIMAL(18,2),
    phuCapKhac DECIMAL(18,2),
    khoanTruBaoHiem DECIMAL(18,2),
    khoanTruKhac DECIMAL(18,2),
    thue DECIMAL(18,2),
    thucLanh DECIMAL(18,2),
    FOREIGN KEY (maBangChamCong) REFERENCES BANGCHAMCONG(maBangChamCong)
);

-- Reviews
CREATE TABLE BANGDANHGIA (
    maDanhGia VARCHAR(20) PRIMARY KEY,
    maNhanVien VARCHAR(20),
    nguoiDanhGia VARCHAR(20),
    ngayDanhGia DATE,
    diemDanhGia INT,
    xepLoaiDanhGia VARCHAR(50),
    chiTietDanhGia LONGTEXT,
    loaiDanhGia VARCHAR(50),
    ghiChu LONGTEXT,
    FOREIGN KEY (maNhanVien) REFERENCES NHANVIEN(maNhanVien),
    FOREIGN KEY (nguoiDanhGia) REFERENCES NHANVIEN(maNhanVien)
);

-- Recruiting
CREATE TABLE BAOCAOTUYENDUNG (
    maTuyenDung VARCHAR(20) PRIMARY KEY,
    chucVu VARCHAR(100),
    hocVan VARCHAR(100),
    yeuCauGioiTinh VARCHAR(20),
    yeuCauDoTuoi VARCHAR(20),
    soLuongCanTuyen INT,
    hanNopHoSo DATE,
    mucLuongToiThieu DECIMAL(18,2),
    mucLuongToiDa DECIMAL(18,2),
    soLuongNhanDuoc INT DEFAULT 0,
    soLuongDaTuyen INT DEFAULT 0
);

-- Candidates
CREATE TABLE UNGVIEN (
    maTuyenDung VARCHAR(20),
    maUngVien VARCHAR(20) PRIMARY KEY,
    CMND VARCHAR(20),
    mucLuongDeal DECIMAL(18,2),
    maTrinhDo VARCHAR(20),
    maChucVu VARCHAR(20),
    trangThai VARCHAR(50),
    FOREIGN KEY (maTuyenDung) REFERENCES BAOCAOTUYENDUNG(maTuyenDung),
    FOREIGN KEY (CMND) REFERENCES CONNGUOI(CMND),
    FOREIGN KEY (maTrinhDo) REFERENCES TRINHDO(maTrinhDo),
    FOREIGN KEY (maChucVu) REFERENCES CHUCVU(maChucVu)
);

-- Statistics
CREATE TABLE THONGKE (
    maDonVi VARCHAR(20) PRIMARY KEY,
    slnvNam1 INT,
    slnvNam2 INT,
    slnvNam3 INT
);

-- Address tables - Cities, Districts, Wards, Streets
CREATE TABLE TINHTHANHPHO (
    maTinhThanhPho VARCHAR(20) PRIMARY KEY,
    tenTinhThanhPho VARCHAR(100)
);

CREATE TABLE QUANHUYEN (
    maQuanHuyen VARCHAR(20) PRIMARY KEY,
    tenQuanHuyen VARCHAR(100),
    maTinhThanhPho VARCHAR(20),
    FOREIGN KEY (maTinhThanhPho) REFERENCES TINHTHANHPHO(maTinhThanhPho)
);

CREATE TABLE PHUONGXA (
    maPhuongXa VARCHAR(20) PRIMARY KEY,
    tenPhuongXa VARCHAR(100),
    maQuanHuyen VARCHAR(20),
    FOREIGN KEY (maQuanHuyen) REFERENCES QUANHUYEN(maQuanHuyen)
);

CREATE TABLE DUONG (
    maDuong VARCHAR(20) PRIMARY KEY,
    tenDuong VARCHAR(100),
    maPhuongXa VARCHAR(20),
    FOREIGN KEY (maPhuongXa) REFERENCES PHUONGXA(maPhuongXa)
);

-- Initial data for statistics
INSERT INTO THONGKE (maDonVi, slnvNam1, slnvNam2, slnvNam3)
VALUES ('CONGTY', 0, 0, 0);

-- Initial admin role group
INSERT INTO NHOMQUYEN (maNhomQuyen, tenNhomQuyen)
VALUES ('ADMIN', 'Quản trị viên');

-- Populate admin permissions (using individual INSERT statements instead of WHILE loop)
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 1);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 2);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 3);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 4);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 5);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 6);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 7);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 8);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 9);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 10);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 11);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 12);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 13);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 14);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 15);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 16);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 17);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 18);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 19);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 20);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 21);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 22);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 23);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 24);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 25);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 26);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 27);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 28);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 29);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 30);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 31);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 32);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 33);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 34);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 35);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 36);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 37);
INSERT INTO CHITIETNHOMQUYEN (maNhomQuyen, maChucNang) VALUES ('ADMIN', 38);