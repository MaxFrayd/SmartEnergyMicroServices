export interface InventoryProduct
{
    id: string;
    category?: string;
    name: string;
    description?: string;
    tags?: string[];
    sku?: string | null;
    barcode?: string | null;
    brand?: string | null;
    vendor: string | null;
    stock: number;
    reserved: number;
    cost: number;
    basePrice: number;
    taxPercent: number;
    price: number;
    weight: number;
    thumbnail: string;
    images: string[];
    active: boolean;
}

export interface InventoryPagination
{
    length: number;
    size: number;
    page: number;
    lastPage: number;
    startIndex: number;
    endIndex: number;
}

export interface InventoryCategory
{
    id: string;
    parentId: string;
    name: string;
    slug: string;
}

export interface InventoryBrand
{
    id: string;
    name: string;
    slug: string;
}

export interface InventoryTag
{
    id?: string;
    title?: string;
}

export interface InventoryVendor
{
    id: string;
    name: string;
    slug: string;
}
//for Supplier BE
export interface PageResponse<T> {
    content: T[];
    number: number;          // current page (0-based)
    size: number;            // page size
    totalElements: number;
    totalPages: number;
}

export interface SupplierPagination {
    page: number;
    size: number;
    total: number;
    totalPages: number;
    sort: string;
    order: 'asc' | 'desc' | '';
    search: string;
}

export interface SupplierSearchDto {
    dataOption?: 'AND' | 'OR';
    searchCriteriaList: Array<{
        key: string;          // field name in BE spec
        operation: 'LIKE' | 'EQ' | 'GT' | 'LT' | 'GTE' | 'LTE' | 'IN';
        value: string | number | boolean;
    }>;
}
// supplier-status.enum.ts
export enum SupplierStatus {
    ACTIVE = 'ACTIVE',
    INACTIVE = 'INACTIVE',
    NEW = 'NEW'
}

// address.model.ts
export interface Address {
    id: number;
    latitude: number;
    longitude: number;
    city: string;
    created: string;   // ISO date string (LocalDateTime in BE)
    updated: string;   // ISO date string
}

// supplier.model.ts
export interface Supplier {
    id: number;
    name: string;
    email: string;
    address: Address;
    energyAmount: number;
    currentEnergyAmount: number;
    pricePrKwt: number;
    status: SupplierStatus;
    created: string;   // ISO date string
    updated: string;   // ISO date string
}
